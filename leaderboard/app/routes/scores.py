from flask import Blueprint, request, jsonify
from app.models.score import Score
from app.db import db
from flask_jwt_extended import jwt_required
from flasgger import swag_from
from app.utils.yml_loader import doc_path

scores_bp = Blueprint('scores', __name__, url_prefix='/scores')

@scores_bp.route('', methods=['POST'])
@swag_from(doc_path('scores', 'create.yml'))
@jwt_required()
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

@scores_bp.route('', methods=['GET'])
@swag_from(doc_path('scores', 'get_all.yml'))
@jwt_required()
def get_scores():
    scores = Score.query.all()
    return jsonify([
        {
            'id': s.id,
            'athlete_id': s.athlete_id,
            'workout_id': s.workout_id,
            'score': s.score
        }
        for s in scores
    ])

@scores_bp.route('/<int:id>', methods=['PUT'])
@swag_from(doc_path('scores', 'update.yml'))
@jwt_required()
def update_score(id):
    score = Score.query.get_or_404(id)
    data = request.get_json()
    score.athlete_id = data.get('athlete_id', score.athlete_id)
    score.workout_id = data.get('workout_id', score.workout_id)
    score.score = data.get('score', score.score)
    db.session.commit()
    return jsonify({
        'id': score.id,
        'athlete_id': score.athlete_id,
        'workout_id': score.workout_id,
        'score': score.score
    })

@scores_bp.route('/<int:id>', methods=['DELETE'])
@swag_from(doc_path('scores', 'delete.yml'))
@jwt_required()
def delete_score(id):
    score = Score.query.get_or_404(id)
    db.session.delete(score)
    db.session.commit()
    return jsonify({'message': 'Score deleted'})