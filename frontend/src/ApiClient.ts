const SERVER_URL = 'http://localhost:8080'
const GET_CHALLENGE = '/challenges/random'
const POST_RESULT = '/attempts'

export const challenge = async () : Promise<Response> => {
    return await fetch(`${SERVER_URL}${GET_CHALLENGE}`)
}

export const sendGuess = async (
    user: string,
    a: number,
    b: number,
    guess: number
): Promise<Response> => {
    return await fetch(`${SERVER_URL}${POST_RESULT}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userAlias: user,
            factorA: a,
            factorB: b,
            guess: guess
        })
    })
}

