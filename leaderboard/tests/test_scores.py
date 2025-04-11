def test_create_score(client, athlete, workout, auth_headers):
    response = client.post(
        "/scores",
        json={"athlete_id": athlete, "workout_id": workout, "score": 275.5},
        headers=auth_headers
    )
    assert response.status_code == 201

def test_get_all_scores(client, auth_headers):
    response = client.get("/scores", headers=auth_headers)
    assert response.status_code == 200

def test_update_score(client, auth_headers, athlete, workout):
    score = client.post(
        "/scores",
        json={"athlete_id": athlete, "workout_id": workout, "score": 300},
        headers=auth_headers
    ).get_json()
    response = client.put(
        f"/scores/{score['id']}",
        json={"score": 310},
        headers=auth_headers
    )
    assert response.status_code == 200

def test_delete_score(client, auth_headers, athlete, workout):
    score = client.post(
        "/scores",
        json={"athlete_id": athlete, "workout_id": workout, "score": 200},
        headers=auth_headers
    ).get_json()
    response = client.delete(f"/scores/{score['id']}", headers=auth_headers)
    assert response.status_code == 200
