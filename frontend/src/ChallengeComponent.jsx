import {useEffect, useState} from "react";
import {challenge, sendGuess} from "./ApiClient";

const ChallengeComponent = () => {
    const [state, setState] = useState({
        a: '',
        b: '',
        user: '',
        message: '',
        guess: 0
    })

    useEffect(() => {
        challenge().then(res => {
            if (res.ok) {
                res.json().then(json => {
                    setState(prevState => ({
                        ...prevState,
                        a: json.factorA,
                        b: json.factorB
                    }))
                })
            } else {
                updateMessage("Can't reach the server")
            }
        })
    }, [])

    const handleChange = (event) => {
        const { name, value } = event.target
        setState(prevState => ({
            ...prevState,
            [name]: value
        }))
    }

    const handleSubmitResult = (event) => {
        event.preventDefault()
        sendGuess(
            state.user,
            state.a,
            state.b,
            state.guess
        ).then(res => {
            if (res.ok) {
                res.json().then(json => {
                    if (json.correct) {
                        updateMessage("Congratulations! Your guess is correct")
                    } else {
                        updateMessage(`Oops! Your guess ${json.resultAttempt} is wrong`)
                    }
                })
            } else {
                updateMessage("Error: server error or unavailable")
            }
        })
    }

    const updateMessage = (m) => {
        setState(prevState => ({
            ...prevState,
            message: m
        }))
    }

    return (
        <div>
            <div>
                <h3>Your new challenge is</h3>
                <h1>{state.a} x {state.b}</h1>
            </div>
            <form onSubmit={handleSubmitResult}>
                <label>
                    Your alias:
                    <input type="text" maxLength="12" name="user" value={state.user} onChange={handleChange}/>
                </label>
                <br/>
                <label>
                    Your guess:
                    <input type="number" min="0" name="guess" value={state.guess} onChange={handleChange}/>
                </label>
                <br/>
                <input type={"submit"} value="Submit"/>
            </form>
            <h4>{state.message}</h4>
        </div>
    )
}

export default ChallengeComponent
