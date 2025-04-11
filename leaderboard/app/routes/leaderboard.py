from flask import Blueprint, jsonify
from app.models.score import Score
from flask_jwt_extended import jwt_required
from flasgger import swag_from

leaderboard_bp = Blueprint('leaderboard', __name__, url_prefix='/leaderboard')

@leaderboard_bp.route('/<int:workout_id>', methods=['GET'])
@swag_from('docs/leaderboard/get.yml')
@jwt_required()
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
