from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from flask_jwt_extended import JWTManager
from flasgger import Swagger
from flask_migrate import upgrade

from app.routes import register_routes
from app.db import db
from app.docs.swagger_template import swagger_template
from app.utils.admin_seeder import seed_admin_user

jwt = JWTManager()
migrate = Migrate()

def create_app(config_class=None):
    app = Flask(__name__)
    if config_class:
        app.config.from_object(config_class)
    else:
        app.config.from_object('config.Config')

    db.init_app(app)
    migrate.init_app(app, db)
    jwt.init_app(app)
    Swagger(app, template=swagger_template)

    with app.app_context():
        upgrade()
        seed_admin_user()
        register_routes(app)

    return app
