swagger_template = {
    "swagger": "2.0",
    "info": {
        "title": "Leaderboard API",
        "version": "1.0",
        "description": "API for managing Leaderboard athletes, workouts and scores"
    },
    "securityDefinitions": {
        "Bearer": {
            "type": "apiKey",
            "name": "Authorization",
            "in": "header",
            "description": "JWT Authorization header using the Bearer scheme. Example: 'Bearer {token}'"
        }
    },
    "security": [{"Bearer": []}],
    "basePath": "/",
    "schemes": ["http"],
}
