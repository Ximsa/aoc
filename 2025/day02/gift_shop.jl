using Pipe: @pipe

content = read("input.txt", String)


#content = """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""

function range_to_ids(ids_range)
    (split(ids_range, "-")
     |> ((lower, higher),) -> (parse(Int64, lower), parse(Int64, higher))
     |> ((lower, higher),) -> range(lower, higher)
     |> ids -> map(repr, ids))
end

function is_invalid(id)
    splitpoint = div(length(id),2)
    id[1:splitpoint] == id[splitpoint+1:end]
end

function is_invalid_part_2(id)
    invalid = false
    for part_size in 1:length(id)-1
        ids = Iterators.partition(id,part_size)
        equal_elements = all(x -> x == first(ids), ids)
        invalid = invalid || equal_elements
    end
    return invalid
end

ids = (content
 |> x -> split(x,",")
 |> x -> map(range_to_ids, x)
 |> Iterators.flatten
 |> collect)

result1 = (ids
           |> x -> filter(is_invalid, x)
           |> ids -> map(id -> parse(Int64, id), ids)
           |> sum)

result2 = (ids
           |> x -> filter(is_invalid_part_2, x)
           |> ids -> map(id -> parse(Int64, id), ids)
           |> sum)
