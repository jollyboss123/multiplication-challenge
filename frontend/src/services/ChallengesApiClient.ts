const SERVER_URL = 'http://localhost:8000'
const GET_CHALLENGE = '/challenges/random'
const POST_RESULT = '/attempts'
const GET_ATTEMPTS_BY_ALIAS = '/attempts/stats?alias='
const GET_USERS_BY_IDS = '/users'

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

export interface Challenge {
    factorA: number
    factorB: number
}

export interface Guess {
    correct: boolean
    resultAttempt?: number
}

export interface Attempt {
    id: number
    factorA: number
    factorB: number
    correct: boolean
    resultAttempt: number
}

export interface User {
    id: number
    alias: string
}

export const getAttempts = async (alias: string): Promise<Response> => {
    return await fetch(`${SERVER_URL}${GET_ATTEMPTS_BY_ALIAS}${alias}`)
}

export const getUsers = async (ids: number[]): Promise<Response> => {
    return await fetch(`${SERVER_URL}${GET_USERS_BY_IDS}/${ids.join(',')}`)
}

