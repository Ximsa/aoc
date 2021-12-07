using Formatting
numbers = map(x -> parse(Int64,x),split(first(readlines(open("input"))), ","))
fuel_cost(pivot, numbers, distance) = sum(map(x -> distance(pivot, x), numbers))
minimum_fuel_cost(numbers, metric) = format(minimum(map(x -> fuel_cost(x, numbers, metric), 0:maximum(numbers))))
minimum_fuel_cost(numbers, (x, y) -> abs(x-y))
minimum_fuel_cost(numbers, (x, y) -> (abs(x-y)*(abs(x-y)+1)) / 2) # small gauss
