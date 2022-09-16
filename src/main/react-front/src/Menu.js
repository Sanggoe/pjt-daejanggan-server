import React, { useEffect, useRef, useState } from 'react';
import MenuHeader from "./MenuHeader"
import MenuNav from './MenuNav';
import Button from '@mui/material/Button';

import Container from '@mui/material/Container';
import MenuFooter from './MenuFooter';
import { Checkbox, FormControlLabel } from '@mui/material';

const containerDefault = {
    padding: 0,
    background: "lightyellow",
    maxWidth: 1004,
    border: "1px solid black",
}

//***************** */
const contentsDefault = {
    width: 340,
    margin: "auto",
    padding: 0,
    border: "1px solid black",
}
// border: "1px solid black",

const menuButtonDefault = {
    borderRadius: 50,
    minHeight: 70,
    width: "100%",
    margin: "5px 0px 5px 0px",
    background: "#E6E6E6",
    color: "#7F7F7F",
    fontSize: "25px",
}

const menuButtonClicked = {
    borderRadius: 50,
    minHeight: 70,
    width: "100%",
    background: "#FFE181",
    color: "#7F7F7F",
    fontSize: "25px",
}
/********************* */

// const buttonsContents = (
//     <div className='contents'
//         style={contentsDefault}>
//         {props.data.map((item) => (
//             <div>
//                 <Button
//                     style={menuButtonDefault}>
//                     {item.label}
//                 </Button>
//                 {/* {info.toggle && info.sub.map((subTitle) => (
//                         <li style={{ listStyle: "none", margin: 0 }}>
//                             <FormControlLabel control={<Checkbox color="success" />} label={subTitle} />
//                         </li>
//                     ))} */}
//             </div>
//         ))}
//         {/* {contents} */}
//     </div>
// )


function MenuContents(props) {

    const [toggles, setToggles] = useState([false, false, false, false, false, false, false, false, false, false]);
    const [lender, setLender] = useState(false);

    const handleClick = (id) => {
        toggles[id] = !toggles[id];
        setToggles(toggles);
        setLender(!lender);
        console.log(toggles)
    }

    useEffect(() => {
        console.log("렌더링");
    }, [lender])
    
    return (
        <div className='contents'
            style={contentsDefault}>
            {props.data.map((item) => (
                <div>
                    <Button
                        id={item.id}
                        style={{
                            borderRadius: 50,
                            minHeight: 70,
                            width: "100%",
                            margin: "5px 0px 5px 0px",
                            color: "#7F7F7F",
                            fontSize: "25px",
                            background: toggles[item.id] === true ? "#FFE181" : "#E6E6E6",
                        }}
                        onClick={(event) => handleClick(event.target.id)}>
                        {item.label}
                    </Button>
                    {item.id < 6 && toggles[item.id] && item.subLabel.map((subTitle) => (
                        <li style={{ listStyle: "none", margin: 0 }}>
                            <FormControlLabel control={<Checkbox color="success" />} label={subTitle} />
                        </li>
                    ))}
                </div>
            ))}
            {/* {contents} */}
            <p>&nbsp;</p>
        </div>
    )
}

export default function Menu() {
    const [menu, setMenu] = useState("암송");

    const menuList = [
        { label: "암송", type: "#FFE180" },
        { label: "통계", type: "#FFE181" },
        { label: "업적", type: "#FFE182" }
    ];

    const buttonInfos1 = [
        { id: 0, label: "LOA", subLabel: ["그리스도인의 확신"] },
        { id: 1, label: "LOC", subLabel: ["그리스도인의 생활지침"] },
        {
            id: 2,
            label: "60구절",
            subLabel: [
                "A - 새로운 삶",
                "B - 그리스도를 전파함",
                "C - 하나님을 의뢰함",
                "D - 그리스도의 자격",
                "E - 그리스도를 닮아감"]
        },
        {
            id: 3,
            label: "DEP",
            subLabel: [
                "1. 구원의 확신",
                "2. Quiet Time",
                "3. 말씀",
                "4. 기도",
                "5. 교제",
                "6. 증거",
                "7. 그리스도의 주재권",
                "8. 세계 비전"]
        },
        {
            id: 4,
            label: "180",
            subLabel: [
                "1. 하나님을 알아감",
                "2. 사랑 안에서 자라감",
                "3. 믿음 안에서 자라감",
                "4. 승리 안에서 ",
                "5. 그리스도를 증거함"]
        },
        {
            id: 5,
            label: "OYO",
            subLabel: [
                "장래에 대한 약속",
                "기도에 대한 약속",]
        }
    ]

    const buttonInfos2 = [
        { id: 6, label: "전체 통계" },
        { id: 7, label: "기간별 통계" },
        { id: 8, label: "목록별 통계" },
        { id: 9, label: "구절별 통계" },
    ]

    let contents = null;

    if (menu === "암송") {
        console.log("암송 메뉴 실행")
        contents = <>
            <MenuContents data={buttonInfos1} menu={1} />
            <MenuFooter menu={1} />
        </>
    }

    else if (menu === "통계") {
        console.log("통계 메뉴 실행")
        contents = <>
            <MenuContents data={buttonInfos2} menu={1} />
            <MenuFooter menu={2} />
        </>
    }

    else if (menu === "업적") {
        console.log("업적 메뉴 실행")
        contents = <>
            <h1>업적 Test Contents</h1>
        </>
    }

    return (
        <Container style={containerDefault}>
            <MenuHeader />
            <MenuNav
                menuList={menuList}
                onChangeMode={(menu) => {
                    setMenu(menu);
                }} />
            {contents}
        </Container>
    )
}
