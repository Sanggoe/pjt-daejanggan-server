import React, { useEffect, useState } from 'react';
import MenuHeader from "./MenuHeader"
import MenuNav from './MenuNav';
import Button from '@mui/material/Button';

import Container from '@mui/material/Container';
import MenuFooter from './MenuFooter';
import { Box, Checkbox, FormControlLabel, Stack } from '@mui/material';

const containerDefault = {
    margin: 0,
    padding: 0,
    background: "lightyellow",
    maxWidth: 1004,
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

function MenuContents() {
    const [buttonInfos, setButtonInfos] = useState([
        { idx: 0, group: "LOA", sub: ["그리스도인의 확신"] },
        { idx: 1, group: "LOC", sub: ["그리스도인의 생활지침"] },
        { idx: 2, group: "60구절", sub: ["A - 새로운 삶", "B - 그리스도를 전파함", "C - 하나님을 의뢰함", "D - 그리스도의 자격", "E - 그리스도를 닮아감"] },
        { idx: 3, group: "DEP", sub: ["1", "2", "3", "4", "5"] },
        { idx: 4, group: "180", sub: ["1", "2", "3", "4", "5"] },
        { idx: 5, group: "OYO", sub: ["1", "2", "3", "4", "5"] }
    ])

    const [button1, setButton1] = useState([{flag: false, style: menuButtonDefault}]);
    const [button2, setButton2] = useState([{flag: false, style: menuButtonDefault}]);
    const [button3, setButton3] = useState([{flag: false, style: menuButtonDefault}]);
    const [button4, setButton4] = useState([{flag: false, style: menuButtonDefault}]);
    const [button5, setButton5] = useState([{flag: false, style: menuButtonDefault}]);
    const [button6, setButton6] = useState([{flag: false, style: menuButtonDefault}]);

    useEffect(() => {
        console.log('바뀔때만 렌더링')
    }, [[button1, button2, button3, button4, button5, button6]])

    // toggle: false, style: menuButtonDefault
    // function onChangeToggle(id) {
    //     if (buttonInfos1[id].toggle) { // true 이면
    //         buttonInfos1[id].toggle = false;
    //         buttonInfos1[id].style = menuButtonDefault;
    //         setButtonInfos1(buttonInfos1);
    //         console.log(buttonInfos1[id].toggle + '로 변경')

    //     } else { // false 면
    //         buttonInfos1[id].toggle = true
    //         buttonInfos1[id].style = menuButtonClicked;
    //         setButtonInfos1(buttonInfos1);
    //         console.log(buttonInfos1[id].toggle + '로 변경')
    //     }
    // }


    // const handleClicked = (event) => {
    //     console.log('handleClicked 호출')
    //     event.preventDefault();
    //     if(!button[event.target.id].toggle) {
    //         buttons[event.target.id].toggle = true;
    //         buttons[event.target.id].style = menuButtonClicked;
    //         setButtons(buttons);
    //     } else {
    //         buttons[event.target.id].toggle = false;
    //         buttons[event.target.id].style = menuButtonDefault;
    //         setButtons(buttons);
    //     }
    // }

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>

            <Stack
                spacing={2}
                sx={stackDefault}>
                {buttonInfos.map((info) => (
                    <>
                        <Button
                            id={info.id}
                            style={menuButtonDefault}
                            >
                            {info.group}
                        </Button>
                        {info.toggle && info.sub.map((subTitle) => (
                            <li style={{listStyle: "none", margin: 0}}>
                                <FormControlLabel control={<Checkbox color="success"/>} label={subTitle} />
                            </li>
                        ))}
                    </>
                ))}

                {/* {contents} */}

            </Stack>
        </Box>
    )
}


export default function Menu() {
    const [menu, setMenu] = useState("암송");

    // const [buttonInfos1, setButtonInfos1] = useState([
    //     { id: 0, group: "LOA", sub: ["그리스도인의 확신"] },
    //     { id: 1, group: "LOC", sub: ["그리스도인의 생활지침"] },
    //     { id: 2, group: "60구절", sub: ["A - 새로운 삶", "B - 그리스도를 전파함", "C - 하나님을 의뢰함", "D - 그리스도의 자격", "E - 그리스도를 닮아감"] },
    //     { id: 3, group: "DEP", sub: ["1", "2", "3", "4", "5"] },
    //     { id: 4, group: "180", sub: ["1", "2", "3", "4", "5"] },
    //     { id: 5, group: "OYO", sub: ["1", "2", "3", "4", "5"] }
    // ])
    // const [buttonInfos1, setButtonInfos1] = useState([
    //     { id: 0, group: "LOA", toggle: false, style: menuButtonDefault, sub: ["그리스도인의 확신"] },
    //     { id: 1, group: "LOC", toggle: false, style: menuButtonDefault, sub: ["그리스도인의 생활지침"] },
    //     { id: 2, group: "60구절", toggle: false, style: menuButtonDefault, sub: ["A - 새로운 삶", "B - 그리스도를 전파함", "C - 하나님을 의뢰함", "D - 그리스도의 자격", "E - 그리스도를 닮아감"] },
    //     { id: 3, group: "DEP", toggle: false, style: menuButtonDefault, sub: ["1", "2", "3", "4", "5"] },
    //     { id: 4, group: "180", toggle: false, style: menuButtonDefault, sub: ["1", "2", "3", "4", "5"] },
    //     { id: 5, group: "OYO", toggle: false, style: menuButtonDefault, sub: ["1", "2", "3", "4", "5"] }
    // ])

    const [buttonInfos2, setButtonInfos2] = useState([
        { id: 10, group: "전체 통계", toggle: false, style: menuButtonDefault, sub: null},
        { id: 11, group: "기간별 통계", toggle: false, style: menuButtonDefault, sub: null},
        { id: 12, group: "목록별 통계", toggle: false, style: menuButtonDefault, sub: null},
        { id: 13, group: "구절별 통계", toggle: false, style: menuButtonDefault, sub: null}
    ])

    let contents = null;

    // function onChangeToggle(id) {
    //     if (buttonInfos1[id].toggle) { // true 이면
    //         buttonInfos1[id].toggle = false;
    //         buttonInfos1[id].style = menuButtonDefault;
    //         setButtonInfos1(buttonInfos1);
    //         console.log(buttonInfos1[id].toggle + '로 변경')

    //     } else { // false 면
    //         buttonInfos1[id].toggle = true
    //         buttonInfos1[id].style = menuButtonClicked;
    //         setButtonInfos1(buttonInfos1);
    //         console.log(buttonInfos1[id].toggle + '로 변경')
    //     }
    // }

    if (menu === "암송") {
        console.log("암송 메뉴 실행")
        contents = <>
            <MenuContents />
            <MenuFooter />
        </>
    }

    else if (menu === "통계") {
        console.log("통계 메뉴 실행")
        contents = <>
            <MenuContents />
        </>
    }

    else if (menu === "업적") {
        console.log("업적 메뉴 실행")
        contents = <>
            <h1>업적 Test</h1>
            <h1>Contents</h1>
        </>
    }



    // useEffect(() => {
    //     axios.get('http://localhost:8080/menu')
    //         .then(response => setItems(response.data))
    //         .catch(error => console.log(error))
    // }, []);

    const menuList = [
        { label: "암송", type: "#FFE180"},
        { label: "통계", type: "#FFE181"},
        { label: "업적", type: "#FFE182"}
    ];

    return (
        <Container style={containerDefault}>
            <MenuHeader />
            <MenuNav
                key={menuList.id}
                menuList={menuList}
                onChangeMode={(menu) => {
                    setMenu(menu);
                }} />
            {contents}
        </Container>
    )
}
