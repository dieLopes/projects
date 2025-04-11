from flask import Flask
import pytest
from app import create_app
from app.db import db
from flask_jwt_extended import create_access_token
from app.models.user import User
from app.models.workout import Workout
from app.models.athlete import Athlete

class TestConfig:
    TESTING = True
    SQLALCHEMY_DATABASE_URI = 'sqlite:///:memory:'
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    JWT_SECRET_KEY = 'test-secret-key'

@pytest.fixture(scope='module')
def app():
    app = create_app(TestConfig)
    with app.app_context():
        db.create_all()
        yield app
        db.drop_all()

@pytest.fixture(scope='module')
def client(app):
    return app.test_client()

@pytest.fixture(scope='module')
def auth_headers(app):
    with app.app_context():
        access_token = create_access_token(identity='test_user')
    return {
        'Authorization': f'Bearer {access_token}'
    }

@pytest.fixture(scope="module", autouse=True)
def create_admin_user(app):
    with app.app_context():
        test = User(username="test")
        test.set_password("test")
        db.session.add(test)
        db.session.commit()

@pytest.fixture(scope='module')
def workout(app):
    with app.app_context():
        workout = Workout(name="WOD Teste", workout_type="AMRAP", description="Teste de 10 min")
        db.session.add(workout)
        db.session.commit()
        return workout.id
    
@pytest.fixture(scope='module')
def athlete(app):
    with app.app_context():
        athlete = Athlete(name="John")
        db.session.add(athlete)
        db.session.commit()
        return athlete.id