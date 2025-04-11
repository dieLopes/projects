def test_get_leaderboard(client, workout, auth_headers):
    response = client.get(f"/leaderboard/{workout}", headers=auth_headers)
    assert response.status_code == 200
    assert isinstance(response.get_json(), list)