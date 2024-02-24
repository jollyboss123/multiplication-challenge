const SERVER_URL = 'http://localhost:8000'
const GET_LEADERBOARD = '/leaders'

export const getLeaderboard = async () : Promise<Response> => {
    return await fetch(`${SERVER_URL}${GET_LEADERBOARD}`)
}

export interface LeaderBoard {
    userId: number
    totalScore: number
    badges: string[]
    alias: string | undefined
}
