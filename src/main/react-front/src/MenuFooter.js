import { Box, Button } from "@mui/material"

const footerBoxDefault = {
    maxWidth: 1000,
    height: 60,
    background: "white",
    position: "fixed",
    bottom: 0,
    width: "100%",
    border: "1px solid black",
    margin: "0px 50px 0px 0px",
}

const footerButtonDefault = {
    minWidth: 200,
    width: "100%",
    color: "black",
    background: "white",
    fontSize: "18px",
}

const buttonBackground = {
    background: "#FFE181",
    borderRadius: 5,
}

export default function MenuFooter() {
    return (
        <Box
            fixed
            display="flex"
            justifyContent="center"
            alignItems="center"
            sx={footerBoxDefault}>
            <div style={buttonBackground}>
                <Button
                    sx={footerButtonDefault}>
                    <b>암송하러 가기</b>
                </Button>
            </div>
            <div style={buttonBackground}>
                <Button
                    sx={footerButtonDefault}>
                    <b>점검하러 가기</b>
                </Button>
            </div>
        </Box>
    )
}