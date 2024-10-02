def price(previousSales, currentPrice, passengers, points):
    priceFactor = (previousSales / 100.0) * 0.8
    finalPrice = currentPrice * priceFactor * passengers
    if passengers > 4:
        finalPrice = finalPrice * 0.95
    if points > 0:
        finalPrice = finalPrice - points * 0.1
    return finalPrice

while True:
    previousSales = float(input("Enter previous sales: "))
    currentPrice = float(input("Enter current price: "))
    passengers = int(input("Enter number of passengers: "))
    points = int(input("Enter points: "))
    print(price(previousSales, currentPrice, passengers, points))