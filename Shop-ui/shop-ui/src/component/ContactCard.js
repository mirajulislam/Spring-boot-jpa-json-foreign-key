import React from "react"
function ContactCard(props){
    return (
        <div className="contact-card">
            <img src={props.contact.imgUrl}/>
            <h3>Name:{props.contact.name}</h3>
            <p>Phones:{props.contact.phone}</p>
            <p>Email:{props.contact.email}</p>
        </div>
    )
}
export default ContactCard 