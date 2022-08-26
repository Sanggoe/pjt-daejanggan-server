import { Button, Box } from "@mui/material"

const boxDefault = {
    maxWidth: 1000,
    minHeight: 80,
    border: "1px solid black",
}
//border: "1px solid black",

const menuButtonDefault = {
    borderRadius: 50,
    minWidth: 100,
    background: "#FFE181",
    color: "black",
    fontSize: "25px",
    margin: 10
}

export default function MenuNav(props) {
    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>
            <Button
                id="암송"
                style={menuButtonDefault}
                onClick={(event) => {
                    event.preventDefault();
                    props.onChangeMode1();
                }}>
                <b>암송</b>
            </Button>
            <Button
                id="통계"
                style={menuButtonDefault}
                onClick={(event) => {
                    event.preventDefault();
                    props.onChangeMode2();
                }}>
                <b>통계</b>
            </Button>
            <Button
                id="업적"
                style={menuButtonDefault}
                onClick={(event) => {
                    event.preventDefault();
                    props.onChangeMode3();
                }}>
                <b>업적</b>
            </Button>
        </Box>
    )
}
