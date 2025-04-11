from flask import Blueprint, request, jsonify
from app.models.workout import Workout
from app.db import db
from flask_jwt_extended import jwt_required
from flasgger import swag_from
from app.utils.yml_loader import doc_path

workouts_bp = Blueprint('workouts', __name__, url_prefix='/workouts')

@workouts_bp.route('', methods=['POST'])
@swag_from(doc_path('scores', 'create.yml'))
@jwt_required()
def create_workout():
    data = request.get_json()
    workout = Workout(name=data['name'], workout_type=data['workout_type'], description=data.get('description', ''))
    db.session.add(workout)
    db.session.commit()
    return jsonify({'id': workout.id, 'name': workout.name, 'workout_type': workout.workout_type, 'description': workout.description, }), 201

@workouts_bp.route('', methods=['GET'])
@swag_from(doc_path('scores', 'get_all.yml'))
@jwt_required()
def get_workouts():
    workouts = Workout.query.all()
    return jsonify([{'id': w.id, 'name': w.name, 'workout_type': w.workout_type, 'description': w.description} for w in workouts])

@workouts_bp.route('/<int:id>', methods=['GET'])
@swag_from(doc_path('scores', 'get_by_id.yml'))
@jwt_required()
def get_workout_by_id(id):
    workout = Workout.query.get_or_404(id)
    return jsonify({'id': workout.id, 'name': workout.name, 'workout_type': workout.workout_type, 'description': workout.description})

@workouts_bp.route('/<int:id>', methods=['PUT'])
@swag_from(doc_path('scores', 'update.yml'))
@jwt_required()
def update_workout(id):
    workout = Workout.query.get_or_404(id)
    data = request.get_json()
    workout.name = data.get('name', workout.name)
    workout.workout_type = data.get('workout_type', workout.workout_type)
    workout.description = data.get('description', workout.description)
    db.session.commit()
    return jsonify({'id': workout.id, 'name': workout.name, 'workout_type': workout.workout_type, 'description': workout.description})

@workouts_bp.route('/<int:id>', methods=['DELETE'])
@swag_from(doc_path('scores', 'delete.yml'))
@jwt_required()
def delete_workout(id):
    workout = Workout.query.get_or_404(id)
    db.session.delete(workout)
    db.session.commit()
    return jsonify({'message': 'Workout deleted'})