import React from "react"
function Joke(props){
    console.log(props);
    return (
        <div>
            <h3 style={{display: !props.question && "none"}}>Questions:{props.question}</h3>
            <h3 style={{color: !props.question && "red"}}>Answer:{props.answer}</h3>
        </div>
    )
}
export default Joke 