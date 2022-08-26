import React, { useEffect, useState } from 'react';
import MenuHeader from "./MenuHeader"
import MenuNav from './MenuNav';
import MenuItems from './MenuItems';

import Button from '@mui/material/Button';


import ButtonGroup from '@mui/material/ButtonGroup';
import Container from '@mui/material/Container';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import MenuFooter from './MenuFooter';
import axios from 'axios';

function LongText() {
    return (
        <>Lorem Ipsum is simply dummy text of the printing and typesetting industry.
        </>
    )
}

function Article() {
    const [open, setOpen] = useState(false);

    return (
        <article style={{ backgroundColor: "orange" }}>
            <h2>Welcome</h2>
            <LongText></LongText>
            <br />
            <ButtonGroup>
                <Button
                    variant="outlined"
                    onClick={() => {
                        setOpen(true);
                    }} >
                    Create</Button>
                <Button variant="outlined">Update</Button>
            </ButtonGroup>
            <Button variant="outlined">Delete</Button>
            <Dialog open={open}>
                <DialogTitle>Create</DialogTitle>
                <DialogContent>
                    <DialogContentText>Hello create!!</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button variant="outlined">CREATE</Button>
                    <Button
                        variant="outlined"
                        onClick={() => {
                            setOpen(false);
                        }}>
                        CANCEL</Button>
                </DialogActions>
            </Dialog>
        </article>)
}


const containerDefault = {
    background: "lightyellow",
    width: "100%",
    border: "1px solid black",
}

export default function Menu() {

    const [items, setItems] = useState([
        ["그리스도인의 확신"],
        ["그리스도인의 생활지침"],
        ["A - 새로운 삶", "B - 그리스도를 전파함", "C - 하나님을 의뢰함", "D - 그리스도의 자격", "E - 그리스도를 닮아감"]]);
    const [mode, setMode] = useState("암송");
    const [toggle, setToggle] = useState(false);

    let content = null;

    if (mode === "암송") {
        content = <>
            <MenuItems
                groups={["LOA", "LOC", "60구절", "DEP", "180", "OYO"]}
                items={items}
                toggle={toggle}
                onChangeToggle={() => {
                    if (toggle) {
                        setToggle(false);
                    } else {
                        setToggle(true);
                    }
                }} />
                
            <MenuFooter />
        </>
    } else if (mode === "통계") {
        content = <>
            <MenuItems
                groups={["전체 통계", "기간별 통계", "목록별 통계", "구절별 통계"]}
                 />
            <MenuFooter />
        </>
    } else if (mode === "업적") {
        content = <>
            <h1>여기는 뭘 쓰지?</h1>
            <h1>예수님께서 우리게 해주신</h1>
            <h1>수 많은 '업적' 들...(?) ^^</h1>
        </>
    }

    // useEffect(() => {
    //     axios.get('http://localhost:8080/menu')
    //         .then(response => setItems(response.data))
    //         .catch(error => console.log(error))
    // }, []);

    return (
        <Container style={containerDefault}>
            <MenuHeader />
            <MenuNav
                onChangeMode1={() => {
                    setMode("암송");
                }}
                onChangeMode2={() => {
                    setMode("통계");
                }}
                onChangeMode3={() => {
                    setMode("업적");
                }} />
            {content}
        </Container>
    )
}