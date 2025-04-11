from .athletes import athletes_bp
from .workouts import workouts_bp
from .scores import scores_bp
from .leaderboard import leaderboard_bp
from .auth import auth_bp

def register_routes(app):
    app.register_blueprint(athletes_bp)
    app.register_blueprint(workouts_bp)
    app.register_blueprint(scores_bp)
    app.register_blueprint(leaderboard_bp)
    app.register_blueprint(auth_bp, url_prefix='/auth')
