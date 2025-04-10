# 4. Fatorial recursivo
def recursive(n):
    if n <= 1:
        return 1
    return n * recursive(n - 1)

def recursive_factorial():
    print("\n=== Fatorial Recursivo ===")
    number = int(input("Número: "))
    return recursive(number)

if __name__ == "__main__":
    print(recursive_factorial())