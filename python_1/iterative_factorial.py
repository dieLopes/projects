# 3. Fatorial iterativo
def iterative_factorial():
    print("\n=== Fatorial Iterativo ===")
    number = int(input("Número: "))
    result = 1
    for i in range(2, number + 1):
        result *= i
    return result

if __name__ == "__main__":
    print(iterative_factorial())