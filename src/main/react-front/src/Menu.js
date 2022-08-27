import React, { useEffect, useState } from 'react';
import MenuHeader from "./MenuHeader"
import MenuNav from './MenuNav';
import Button from '@mui/material/Button';


import ButtonGroup from '@mui/material/ButtonGroup';
import Container from '@mui/material/Container';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import MenuFooter from './MenuFooter';
import { Box, Stack } from '@mui/material';
// import MenuContents from './MenuContents';

// function LongText() {
//     return (
//         <>Lorem Ipsum is simply dummy text of the printing and typesetting industry.
//         </>
//     )
// }

// function Article() {
//     const [open, setOpen] = useState(false);

//     return (
//         <article style={{ backgroundColor: "orange" }}>
//             <h2>Welcome</h2>
//             <LongText></LongText>
//             <br />
//             <ButtonGroup>
//                 <Button
//                     variant="outlined"
//                     onClick={() => {
//                         setOpen(true);
//                     }} >
//                     Create</Button>
//                 <Button variant="outlined">Update</Button>
//             </ButtonGroup>
//             <Button variant="outlined">Delete</Button>
//             <Dialog open={open}>
//                 <DialogTitle>Create</DialogTitle>
//                 <DialogContent>
//                     <DialogContentText>Hello create!!</DialogContentText>
//                 </DialogContent>
//                 <DialogActions>
//                     <Button variant="outlined">CREATE</Button>
//                     <Button
//                         variant="outlined"
//                         onClick={() => {
//                             setOpen(false);
//                         }}>
//                         CANCEL</Button>
//                 </DialogActions>
//             </Dialog>
//         </article>)
// }


const containerDefault = {
    background: "lightyellow",
    width: "100%",
    border: "1px solid black",
}

//***************** */
const boxDefault = {
    maxWidth: 1000,
    minHeight: 400,
    border: "1px solid black",
}
// border: "1px solid black",

const stackDefault = {
    minWidth: 300,
    margin: 3,
    border: "1px solid black",
}
// border: "1px solid black",

const menuButtonDefault = {
    borderRadius: 50,
    minHeight: 65,
    background: "#E6E6E6",
    color: "#7F7F7F",
    fontSize: "25px",
}

const menuButtonClicked = {
    borderRadius: 50,
    minHeight: 65,
    background: "#FFE181",
    color: "#7F7F7F",
    fontSize: "25px",
}
/********************* */

function MenuContents(props) {

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>

            <Stack
                spacing={2}
                sx={stackDefault}>
                {props.buttonInfos.map((info) => (
                    <>
                        <Button
                            id={info.id}
                            style={info.style}
                            onClick={(event) => {
                                event.preventDefault();
                                props.onChangeToggle(event.target.id);
                            }}
                            href="./">
                            {info.group}
                        </Button>
                        {props.subContents}
                    </>
                ))}

                {/* {contents} */}

            </Stack>
        </Box>
    )
}



export default function Menu() {
    const [menu, setMenu] = useState("암송");
    
    const [value, setValue] = useState("");
    
    const [buttonInfos1, setButtonInfos1] = useState([
        { id: 0, group: "LOA", toggle: false, style: menuButtonDefault},
        { id: 1, group: "LOC", toggle: false, style: menuButtonDefault},
        { id: 2, group: "60구절", toggle: false, style: menuButtonDefault},
        { id: 3, group: "DEP", toggle: false, style: menuButtonDefault},
        { id: 4, group: "180", toggle: false, style: menuButtonDefault},
        { id: 5, group: "OYO", toggle: false, style: menuButtonDefault}
    ])

    const [items, setItems] = useState([
        ["그리스도인의 확신"],
        ["그리스도인의 생활지침"],
        ["A - 새로운 삶", "B - 그리스도를 전파함", "C - 하나님을 의뢰함", "D - 그리스도의 자격", "E - 그리스도를 닮아감"]]);

    const [buttonInfos2, setButtonInfos2] = useState([
        { id: 10, group: "전체 통계", toggle: false, style: menuButtonDefault },
        { id: 11, group: "기간별 통계", toggle: false, style: menuButtonDefault },
        { id: 12, group: "목록별 통계", toggle: false, style: menuButtonDefault },
        { id: 13, group: "구절별 통계", toggle: false, style: menuButtonDefault }
    ])

    function onChangeToggle(id) {
        if(buttonInfos1[id].toggle) { // true 이면
            setValue(<p></p>);
            buttonInfos1[id].toggle = false;
            buttonInfos1[id].style = menuButtonDefault;
            setButtonInfos1(buttonInfos1);
            console.log(buttonInfos1[id].toggle + '로 변경')
        } else { // false 면
            setValue();
            buttonInfos1[id].toggle = true
            buttonInfos1[id].style = menuButtonClicked;
            setButtonInfos1(buttonInfos1);
            console.log(buttonInfos1[id].toggle + '로 변경')
        }
    }

    let contents = null;
    let subContents = null;

    if (menu === "암송") {

        contents = <>
            <MenuContents
                buttonInfos={buttonInfos1}
                subContents={subContents}
                items={items}
                onChangeToggle={onChangeToggle}
                />
            <MenuFooter />
        </>
    } else if (menu === "통계") {
        contents = <>
            <MenuContents
                buttonInfos={buttonInfos2} />
        </>
    } else if (menu === "업적") {
        contents = <>
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
                menus={["암송", "통계", "업적"]}
                onChangeMode={(menu) => {
                    setMenu(menu);
                }} />
            {contents}
        </Container>
    )
}
