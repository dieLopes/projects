# app/routes.py
from flask import Blueprint, request, jsonify
from app.models import db, Athlete, Workout, Score

api_bp = Blueprint('api', __name__)


@api_bp.route('/athletes', methods=['POST'])
def create_athlete():
    data = request.get_json()
    athlete = Athlete(name=data['name'])
    db.session.add(athlete)
    db.session.commit()
    return jsonify({'id': athlete.id, 'name': athlete.name}), 201


@api_bp.route('/workouts', methods=['POST'])
def create_workout():
    data = request.get_json()
    workout = Workout(name=data['name'], description=data.get('description', ''))
    db.session.add(workout)
    db.session.commit()
    return jsonify({'id': workout.id, 'name': workout.name}), 201


@api_bp.route('/scores', methods=['POST'])
def create_score():
    data = request.get_json()
    score = Score(
        athlete_id=data['athlete_id'],
        workout_id=data['workout_id'],
        score=data['score']
    )
    db.session.add(score)
    db.session.commit()
    return jsonify({'id': score.id, 'score': score.score}), 201


@api_bp.route('/leaderboard/<int:workout_id>', methods=['GET'])
def get_leaderboard(workout_id):
    scores = Score.query.filter_by(workout_id=workout_id).order_by(Score.score.desc()).all()
    leaderboard = [
        {
            'athlete': score.athlete.name,
            'score': score.score
        }
        for score in scores
    ]
    return jsonify(leaderboard)
