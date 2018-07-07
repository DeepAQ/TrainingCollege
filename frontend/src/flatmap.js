export default function (map, ratio = 1) {
    let result = []
    for (const key in map) {
        result.push([key, map[key] * ratio])
    }
    return result
}