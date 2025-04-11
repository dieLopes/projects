from app.db import db

class Workout(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    workout_type = db.Column(db.String(50), nullable=False) 
    description = db.Column(db.Text)