# 1. Pode dirigir?
def can_drive():
    print("=== Pode Dirigir ===")
    name = input("Nome: ")
    years = int(input("Idade: "))
    if years >= 18:
        print(f"{name} pode dirigir.")
    else:
        print(f"{name} não pode dirigir.")

# TESTES
if __name__ == "__main__":
    can_drive()