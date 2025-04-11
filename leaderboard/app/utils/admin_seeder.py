# app/utils/admin_seeder.py

from app.models.user import User
from app.db import db
from werkzeug.security import generate_password_hash

def seed_admin_user():
    if not User.query.filter_by(username="admin").first():
        admin = User(
            username="admin",
            password=generate_password_hash("admin123")
        )
        db.session.add(admin)
        db.session.commit()
        print("✅ Admin user created: admin / admin123")
