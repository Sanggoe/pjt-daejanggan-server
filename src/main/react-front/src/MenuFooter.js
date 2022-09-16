import { Button } from "@mui/material"

const footerBoxDefault = {
    maxWidth: 1000,
    position: "fixed",
    bottom: 0,
    width: "100%",
    border: "1px solid black",
    background: "#FFE181",
}

const footerButtonDefault2 = {
    minWidth: 200,
    width: "50%",
    color: "black",
    background: "white",
    fontSize: "20px",
    borderRadius: 0,
}

const footerButtonDefault1 = {
    minWidth: 200,
    width: "100%",
    color: "black",
    background: "white",
    fontSize: "20px",
    borderRadius: 0,
}

export default function MenuFooter(props) {

    if (props.menu === 1) {
        return (
            <div
                style={footerBoxDefault}>
                <Button
                    sx={footerButtonDefault2}>
                    <b>확인하러 가기</b>
                </Button>
                <Button
                    sx={footerButtonDefault2}>
                    <b>정리하러 가기</b>
                </Button>
            </div>
        )
    } else if (props.menu === 2) {
        return (
            <div
                style={footerBoxDefault}>
                <Button
                    sx={footerButtonDefault1}>
                    <b>보러 가기</b>
                </Button>
            </div>
        )
    }
}