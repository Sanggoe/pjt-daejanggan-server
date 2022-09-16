import { Button, Checkbox, FormControlLabel } from "@mui/material";
import { Container } from "@mui/system";
import { useState } from "react";
import MenuFooter from "./MenuFooter";
import MenuHeader from "./MenuHeader";
import MenuNav from "./MenuNav";

const containerDefault = {
    margin: 0,
    padding: 0,
    background: "lightyellow",
    maxWidth: 1004,
    border: "1px solid black",
}

const menuButtonDefault = {
    borderRadius: 50,
    minHeight: 65,
    minWidth: 200,
    background: "#E6E6E6",
    color: "#7F7F7F",
    fontSize: "25px",
}
const menuButtonClicked = {
    borderRadius: 50,
    minHeight: 65,
    minWidth: 200,
    background: "#FFE181",
    color: "#7F7F7F",
    fontSize: "25px",
}

function MenuContents() {
    // const datas = [
    //     {
    //         key: "button1", value: "1번째 버튼", func: () => {
    //             if (toggle1) {
    //                 setButtonStyle1(menuButtonDefault)
    //             } else {
    //                 setButtonStyle1(menuButtonClicked) }
    //             setToggle1(!toggle1)
    //         }
    //     },
    //     {
    //         key: "button2", value: "2번째 버튼", func: () => {
    //         if (toggle2) {
    //             setButtonStyle2(menuButtonDefault)
    //         } else {
    //             setButtonStyle2(menuButtonClicked) }
    //         setToggle2(!toggle2)
    //     }
    //     },
    //     {
    //         key: "button3", value: "3번째 버튼", func: () => {
    //             if (toggle3) {
    //                 setButtonStyle3(menuButtonDefault)
    //             } else {
    //                 setButtonStyle3(menuButtonClicked) }
    //             setToggle3(!toggle3)
    //         }
    //     },
    //     {
    //         key: "button4", value: "4번째 버튼", func: () => {
    //             if (toggle4) {
    //                 setButtonStyle4(menuButtonDefault)
    //             } else {
    //                 setButtonStyle4(menuButtonClicked) }
    //             setToggle4(!toggle4)
    //         }
    //     },
    //     {
    //         key: "button5", value: "5번째 버튼", func: () => {
    //             if (toggle5) {
    //                 setButtonStyle5(menuButtonDefault)
    //             } else {
    //                 setButtonStyle5(menuButtonClicked) }
    //             setToggle5(!toggle5)
    //         }
    //     },
    //     {
    //         key: "button6", value: "6번째 버튼", func: () => {
    //             if (toggle6) {
    //                 setButtonStyle6(menuButtonDefault)
    //             } else {
    //                 setButtonStyle6(menuButtonClicked) }
    //             setToggle6(!toggle6)
    //         }
    //     },
    // ]

    const [color, setColor] = useState('');

    const data = [
        {label: "button1", toggle: false, type: 'red'},
        {label: "button2", toggle: false, type: 'red'},
        {label: "button3", toggle: false, type: 'red'},
        {label: "button4", toggle: false, type: 'red'},
    ]

    const handleClick = (type) => {
        setColor(type)
    }

    return (
        <div className='App'>
            {
                data.map(({label, toggle, type}) => (
                    <Button
                        onClick={() => handleClick(toggle, type)}
                        style={{ color: toggle === type ? color : 'black'}}
                    >
                        {label}
                    </Button>
                ))
            }
        </div>
    )

    // const [toggle1, setToggle1] = useState(false);
    // const [toggle2, setToggle2] = useState(false);
    // const [toggle3, setToggle3] = useState(false);
    // const [toggle4, setToggle4] = useState(false);
    // const [toggle5, setToggle5] = useState(false);
    // const [toggle6, setToggle6] = useState(false);

    // const [buttonStyle1, setButtonStyle1] = useState(menuButtonDefault);
    // const [buttonStyle2, setButtonStyle2] = useState(menuButtonDefault);
    // const [buttonStyle3, setButtonStyle3] = useState(menuButtonDefault);
    // const [buttonStyle4, setButtonStyle4] = useState(menuButtonDefault);
    // const [buttonStyle5, setButtonStyle5] = useState(menuButtonDefault);
    // const [buttonStyle6, setButtonStyle6] = useState(menuButtonDefault);

    // let contents1 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="First subTitle" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Second subTitle" />
    //         </li>
    //     </div>

    // let contents2 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Third subTitle" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Fourth subTitle" />
    //         </li>
    //     </div>

    // let contents3 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Fifth subTitle" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Sixth subTitle" />
    //         </li>
    //     </div>

    // let contents4 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="I'm Hungry" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="I want to eat pizza" />
    //         </li>
    //     </div>

    // let contents5 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Hamberger" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Chicken" />
    //         </li>
    //     </div>

    // let contents6 =
    //     <div>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Hello, World" />
    //         </li>
    //         <li style={{ listStyle: "none", margin: 0 }}>
    //             <FormControlLabel control={<Checkbox color="success" />} label="Hi guys" />
    //         </li>
    //     </div>

    // return (
    //     <>
    //         {datas.map((item) => (
    //             <div>
    //                 <Button
    //                     key={item.key}
    //                     style={buttonStyle1}
    //                     onClick={() => item.func}>
    //                     {item.value}
    //                 </Button>
    //                 {toggle1 && contents1} 
    //             </div>
    //         ))}
    //     </>
    // )
}


export default function TestButton() {
    const [menu, setMenu] = useState("암송");

    let contents = null;

    const list = [
        {label: "암송하러 가기"},
        {label: "점검하러 가기"},
    ]

    if (menu === "암송") {
        console.log("암송 메뉴 실행")
        contents = <>
            <MenuContents />
            <h1>암송! Hello</h1>
            <MenuFooter list={list}/>
        </>
    }

    else if (menu === "통계") {
        console.log("통계 메뉴 실행")
        contents = <>
            <h1>통계! Good</h1>
        </>
    }

    else if (menu === "업적") {
        console.log("업적 메뉴 실행")
        contents = <>
            <h1>업적! Test</h1>
        </>
    }

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


/*


    const handleClicked1 = () => {
        if (toggle1) {
            setButtonStyle1(menuButtonDefault)
        } else {
            setButtonStyle1(menuButtonClicked)
        }
        setToggle1(!toggle1)
    }

    const handleClicked2 = () => {
        if (toggle2) {
            setButtonStyle2(menuButtonDefault)
        } else {
            setButtonStyle2(menuButtonClicked)
        }
        setToggle2(!toggle2)
    }

    const handleClicked3 = () => {
        if (toggle3) {
            setButtonStyle3(menuButtonDefault)
        } else {
            setButtonStyle3(menuButtonClicked)
        }
        setToggle3(!toggle3)
    }

    const handleClicked4 = () => {
        if (toggle4) {
            setButtonStyle4(menuButtonDefault)
        } else {
            setButtonStyle4(menuButtonClicked)
        }
        setToggle4(!toggle4)
    }

    const handleClicked5 = () => {
        if (toggle5) {
            setButtonStyle5(menuButtonDefault)
        } else {
            setButtonStyle5(menuButtonClicked)
        }
        setToggle5(!toggle5)
    }

    const handleClicked6 = () => {
        if (toggle6) {
            setButtonStyle6(menuButtonDefault)
        } else {
            setButtonStyle6(menuButtonClicked)
        }
        setToggle6(!toggle6)
    }





    <div>
                <Button
                    style={buttonStyle1}
                    onClick={handleClicked1}>
                    
                </Button>
                {toggle1 && contents1}
            </div>
            <div>
                <Button
                    style={buttonStyle2}
                    onClick={handleClicked2}>
                    2번째 버튼
                </Button>
                {toggle2 && contents2}
            </div>
            <div>
                <Button
                    style={buttonStyle3}
                    onClick={handleClicked3}>
                    3번째 버튼
                </Button>
                {toggle3 && contents3}
            </div>
            <div>
                <Button
                    style={buttonStyle4}
                    onClick={handleClicked4}>
                    4번째 버튼
                </Button>
                {toggle4 && contents4}
            </div>
            <div>
                <Button
                    style={buttonStyle5}
                    onClick={handleClicked5}>
                    5번째 버튼
                </Button>
                {toggle5 && contents5}
            </div>
            <div>
                <Button
                    style={buttonStyle6}
                    onClick={handleClicked6}>
                    6번째 버튼
                </Button>
                {toggle6 && contents6}
            </div>
*/