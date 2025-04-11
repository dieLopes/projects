import json

def test_create_workout(client, auth_headers):
    response = client.post(
        "/workouts",
        json={"name": "Fran", "workout_type": "FOR_TIME", "description": "21-15-9 Thrusters and Pull-ups"},
        headers=auth_headers
    )
    assert response.status_code == 201

def test_get_all_workouts(client, auth_headers):
    response = client.get("/workouts", headers=auth_headers)
    assert response.status_code == 200

def test_get_workout_by_id(client, auth_headers):
    workout = client.post(
        "/workouts",
        json={"name": "Helen", "workout_type": "FOR_TIME", "description": "Run 400m, 21 KB swings, 12 pull-ups"},
        headers=auth_headers
    ).get_json()
    response = client.get(f"/workouts/{workout['id']}", headers=auth_headers)
    assert response.status_code == 200

def test_update_workout(client, auth_headers):
    workout = client.post(
        "/workouts",
        json={"name": "Old WOD", "workout_type": "FOR_TIME", "description": "desc"},
        headers=auth_headers
    ).get_json()
    response = client.put(
        f"/workouts/{workout['id']}",
        json={"name": "New WOD", "workout_type": "FOR_TIME", "description": "Updated"},
        headers=auth_headers
    )
    assert response.status_code == 200

def test_delete_workout(client, auth_headers):
    workout = client.post(
        "/workouts",
        json={"name": "DeleteMe", "workout_type": "FOR_TIME", "description": "desc"},
        headers=auth_headers
    ).get_json()
    response = client.delete(f"/workouts/{workout['id']}", headers=auth_headers)
    assert response.status_code == 200