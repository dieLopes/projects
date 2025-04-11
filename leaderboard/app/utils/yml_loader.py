import os

def doc_path(*paths):
    return os.path.join(os.path.dirname(os.path.dirname(__file__)), 'docs', *paths)