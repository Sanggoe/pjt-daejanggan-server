import React, { useState } from 'react';
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

export default function Menu() {
    return (
        <Container fixed>
            <MenuHeader></MenuHeader>
            <MenuNav></MenuNav>
            <MenuItems groups={["LOA", "LOC", "60구절", "DEP", "180", "OYO"]}></MenuItems>
            <MenuFooter></MenuFooter>
        </Container>
    )
}