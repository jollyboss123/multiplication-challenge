import React, { useEffect, useState } from "react";
import {Attempt, challenge, getAttempts, sendGuess, Challenge, Guess} from "../services/ChallengesApiClient";
import LastAttemptsComponent from "./LastAttemptsComponent";
import LeaderBoardComponent from "./LeaderBoardComponent";

type ChallengeState = {
    a: number;
    b: number;
    user: string;
    guess: number;
    message: string;
    lastAttempts: Attempt[];
};

const ChallengeComponent: React.FC = () => {
    const [{ a, b, user, guess, message, lastAttempts }, setState] = useState<ChallengeState>({
        a: 0,
        b: 0,
        user: '',
        guess: 0,
        message: '',
        lastAttempts: []
    });

    useEffect(() => {
        challenge().then((res: Response) => {
            if (res.ok) {
                res.json().then((json: Challenge) => {
                    setState(prev => ({ ...prev, a: json.factorA, b: json.factorB }));
                });
            } else {
                updateMessage("Can't reach the server");
            }
        });
    }, []);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setState(prev => ({ ...prev, [name]: name === "guess" ? parseInt(value, 10) : value }));
    };

    const handleSubmitResult = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        sendGuess(user, a, b, guess).then((res: Response) => {
            if (res.ok) {
                res.json().then((json: Guess) => {
                    const newMessage = json.correct ? "Congratulations! Your guess is correct" : `Oops! Your guess ${json.resultAttempt} is wrong`;
                    updateMessage(newMessage);
                    updateLastAttempts(user);
                });
            } else {
                updateMessage("Error: server error or unavailable");
            }
        });
    };

    const updateMessage = (m: string) => {
        setState(prev => ({ ...prev, message: m }));
    };

    const updateLastAttempts = (userAlias: string) => {
        getAttempts(userAlias).then((res: Response) => {
            if (res.ok) {
                res.json().then((data) => {
                    setState(prev => ({ ...prev, lastAttempts: data }));
                });
            }
        });
    };

    return (
        <div className={"display-column"}>
            <div>
                <h3>Your new challenge is</h3>
                <h1>{a} x {b}</h1>
            </div>
            <form onSubmit={handleSubmitResult}>
                <div className={"form-container"}>
                    <label>
                        Your alias:
                        <input type="text" maxLength={12} name="user" value={user} onChange={handleChange}/>
                    </label>
                </div>
                {/*<br/>*/}
                <div className={"form-container"}>
                    <label>
                        Your guess:
                        <input type="number" min={0} name="guess" value={guess} onChange={handleChange}/>
                    </label>
                </div>
                {/*<br/>*/}
                <div className={"form-container"}>
                    <input type="submit" value="Submit"/>
                </div>
            </form>
            <h4>{message}</h4>
            {lastAttempts.length > 0 && <LastAttemptsComponent lastAttempts={lastAttempts}/>}
            <LeaderBoardComponent />
        </div>
    );
};

export default ChallengeComponent
