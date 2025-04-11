from flask import Blueprint, request, jsonify
from app.db import db
from app.models.athlete import Athlete
from flask_jwt_extended import jwt_required
from flasgger import swag_from
from app.utils.yml_loader import doc_path

athletes_bp = Blueprint('athletes', __name__, url_prefix='/athletes')

@athletes_bp.route('', methods=['POST'])
@swag_from(doc_path('athletes', 'create.yml'))
@jwt_required()
def create_athlete():
    data = request.get_json()
    athlete = Athlete(name=data['name'])
    db.session.add(athlete)
    db.session.commit()
    return jsonify({'id': athlete.id, 'name': athlete.name}), 201

@athletes_bp.route('', methods=['GET'])
@swag_from(doc_path('athletes', 'get_all.yml'))
@jwt_required()
def get_athletes():
    athletes = Athlete.query.all()
    return jsonify([{'id': a.id, 'name': a.name} for a in athletes])

@athletes_bp.route('/<int:id>', methods=['GET'])
@swag_from(doc_path('athletes', 'get_by_id.yml'))
@jwt_required()
def get_athlete_by_id(id):
    athlete = Athlete.query.get_or_404(id)
    return jsonify({'id': athlete.id, 'name': athlete.name})

@athletes_bp.route('/<int:id>', methods=['PUT'])
@swag_from(doc_path('athletes', 'update.yml'))
@jwt_required()
def update_athlete(id):
    athlete = Athlete.query.get_or_404(id)
    data = request.get_json()
    athlete.name = data.get('name', athlete.name)
    db.session.commit()
    return jsonify({'id': athlete.id, 'name': athlete.name})

@athletes_bp.route('/<int:id>', methods=['DELETE'])
@swag_from(doc_path('athletes', 'delete.yml'))
@jwt_required()
def delete_athlete(id):
    athlete = Athlete.query.get_or_404(id)
    db.session.delete(athlete)
    db.session.commit()
    return jsonify({'message': 'Athlete deleted'})