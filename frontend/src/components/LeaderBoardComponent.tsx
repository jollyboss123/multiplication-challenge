import React, {useCallback, useEffect, useState} from "react";
import {getLeaderboard, LeaderBoard} from "../services/GameApiClient";
import {getUsers, User} from "../services/ChallengesApiClient";

type LeaderBoardState = {
    leaderboard: LeaderBoard[],
    serverError: boolean
}

const LeaderBoardComponent: React.FC = () => {
    const [{leaderboard, serverError}, setState] = useState<LeaderBoardState>({
        leaderboard: [],
        serverError: false
    })

    const refreshLeaderBoard = useCallback(() => {
        getLeaderBoardData().then(
            data => {
                let userIds = data.map(row => row.userId)
                getUsersAliasData(userIds).then(userData => {
                    let userMap : Map<number, string> = new Map()
                    userData.forEach(user => {
                        userMap.set(user.id, user.alias)
                    })

                    data.forEach(row => row['alias'] = userMap.get(row.userId))
                    updateLeaderBoard(data)
                }).catch(reason => {
                    console.log('error mapping user ids', reason)
                    updateLeaderBoard(data)
                })
            }
        ).catch(reason => {
            setState(prev => ({...prev, serverError: true}))
            console.log('gamification server error', reason)
        })
    }, [])

    useEffect(() => {
        refreshLeaderBoard()
        const intervalId = setInterval(refreshLeaderBoard.bind(this), 5000)

        return () => clearInterval(intervalId)
    },[refreshLeaderBoard])

    const getLeaderBoardData = async (): Promise<LeaderBoard[]> => {
        const res = await getLeaderboard();
        if (res.ok) {
            return res.json();
        } else {
            return Promise.reject("gamification: error response");
        }
    }

    const getUsersAliasData = async (ids: number[]): Promise<User[]> => {
        const res = await getUsers(ids);
        if (res.ok) {
            return res.json();
        } else {
            return Promise.reject("multiplication: error response");
        }
    }

    const updateLeaderBoard = (lb: LeaderBoard[]) => {
        setState({
            leaderboard: lb,
            serverError: false
        })
    }

    if (serverError) {
        return (
            <div>
                We're sorry, but we can't display game statistics at this moment.
            </div>
        )
    } else {
        return (
            <div>
                <h3>Leaderboard</h3>
                <table>
                    <thead>
                    <tr>
                        <th>User</th>
                        <th>Score</th>
                        <th>Badges</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        leaderboard.map(row =>
                            <tr key={row.userId}>
                                <td>{row.alias ? row.alias : row.userId}</td>
                                <td>{row.totalScore}</td>
                                <td>{row.badges.map(b => <span className={"badge"} key={b}>{b}</span>)}</td>
                            </tr>
                        )
                    }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default LeaderBoardComponent
