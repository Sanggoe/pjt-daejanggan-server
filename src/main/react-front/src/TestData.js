import React, { useEffect, useState } from 'react';
import Container from '@mui/material/Container';
import axios from 'axios';

function DataAdd(props) {
    return (
        <article>
            <h2>Create</h2>
            <form onSubmit={event => {
                event.preventDefault();
                const title = event.target.title.value
                const body = event.target.body.value
                props.onCreate(title, body);
            }}>
                <p><input type='text' name='title' placeholder='title' /></p>
                <p><textarea name='body' placeholder='body' /></p>
                <p><input type='submit' value='Create' /></p>
            </form>
        </article>
    );
}

function DataReceive(props) {
    return (
        <div>
                <div>
                    <h1>ID : {props.datas}</h1>
                </div>
        </div>
    )
}
//<h1>PW : {data.pw}</h1>

/*
{props.datas.map((data) => (
))}
*/

export default function TestData() {

    const [datas, setDatas] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/menu")
            .then((response) => {
                if (response.data) {
                    setDatas(response.data);
                    console.log(response.data)
                } else {
                    alert("failed to ");
                }
            });
    }, []);

    return (
        <Container fixed>
            <DataReceive datas={datas} />
        </Container>
    )
}


/**

onClick={(event) => {
                    event.preventDefault();
                }}
                onPrint={(id, pw) => {
                    const newData = { id: id, pd: pw };
                    setDatas([...datas, newData])
                }}

<DataAdd
    onClick={(event) => {
        event.preventDefault();
    }}
    onSave={(_id, _pw) => {
        const newData = { id: _id, pd: _pw };
        setDatas([...datas, newData])
    }}>
</DataAdd>
 
*/