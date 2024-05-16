import React, { useState, useEffect } from "react";
import TokenService from "../servicios/TokenService";

const Token = ({ children }) => {
    const [token, setToken] = useState('');

    useEffect(() => {
        TokenService.getToken()
            .then(response => {
                setToken(response);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return <>{children(token)}</>;
}

export default Token;