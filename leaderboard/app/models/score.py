from app.db import db

class Score(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    athlete_id = db.Column(db.Integer, db.ForeignKey('athlete.id'), nullable=False)
    workout_id = db.Column(db.Integer, db.ForeignKey('workout.id'), nullable=False)
    score = db.Column(db.Float, nullable=False)

    athlete = db.relationship('Athlete', backref=db.backref('scores', lazy=True))
    workout = db.relationship('Workout', backref=db.backref('scores', lazy=True))