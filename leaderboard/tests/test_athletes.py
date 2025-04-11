import json
from app.models.athlete import Athlete

def test_create_athlete(client, auth_headers):
    res = client.post('/athletes', json={'name': 'John'}, headers=auth_headers)
    assert res.status_code == 201
    data = res.get_json()
    assert 'id' in data
    assert data['name'] == 'John'

def test_get_athletes(client, auth_headers):
    # Cria um atleta pra garantir que tenha pelo menos um
    client.post('/athletes', json={'name': 'Test Athlete'}, headers=auth_headers)

    res = client.get('/athletes', headers=auth_headers)
    assert res.status_code == 200
    data = res.get_json()
    assert isinstance(data, list)
    assert any(a['name'] == 'Test Athlete' for a in data)

def test_update_athlete(client, auth_headers):
    # Cria um atleta para atualizar
    res = client.post('/athletes', json={'name': 'ToUpdate'}, headers=auth_headers)
    athlete_id = res.get_json()['id']

    res = client.put(f'/athletes/{athlete_id}', json={'name': 'Updated'}, headers=auth_headers)
    assert res.status_code == 200
    data = res.get_json()
    assert data['name'] == 'Updated'

def test_delete_athlete(client, auth_headers):
    # Cria um atleta para deletar
    res = client.post('/athletes', json={'name': 'ToDelete'}, headers=auth_headers)
    athlete_id = res.get_json()['id']

    res = client.delete(f'/athletes/{athlete_id}', headers=auth_headers)
    assert res.status_code == 200

    # Garante que foi removido
    res = client.get('/athletes', headers=auth_headers)
    assert not any(a['id'] == athlete_id for a in res.get_json())
