import json

def test_login_success(client):
    response = client.post("/auth/login", json={
        "username": "test",
        "password": "test"
    })
    assert response.status_code == 200
    data = response.get_json()
    assert "access_token" in data

def test_login_failure(client):
    response = client.post("/auth/login", json={
        "username": "test",
        "password": "wrongpassword"
    })
    assert response.status_code == 401

def test_protected_route_requires_token(client):
    response = client.get("/athletes")
    assert response.status_code == 401

def test_access_protected_route_with_token(client, auth_headers):
    response = client.get(
        "/athletes",
        headers=auth_headers
    )
    
    assert response.status_code == 200
    assert isinstance(response.get_json(), list)
