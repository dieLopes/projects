# 2. Números pares e ímpares
def print_numbers():
    numbers = input("Digite os números separados por vírgula: ")
    listNumbers = list(map(int, numbers.split(",")))
    even = []
    odd = []
    for number in listNumbers:
        if number % 2 == 0:
            even.append(number)
        else:
            odd.append(number)

    print("Números pares:", even)
    print("Números ímpares:", odd)

if __name__ == "__main__":
    print_numbers()