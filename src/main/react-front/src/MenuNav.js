import { Button, Box } from "@mui/material"

const boxDefault = {
    height: 50,
    border: "1px solid black",
    padding: 0,
    maxWidth: 1000,
    minWidth: 298,
    m: 1
}

const menuButtonDefault = {
    borderRadius: 20,
    minWidth: 80,
    background: "#FFE181",
    color: "black",
    fontSize: "18px",
    padding: 2,
    margin: 8
}

export default function MenuNav() {
    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>
            <Button variant="outlined" disabled style={menuButtonDefault} href="./" >
                <b>암송</b>
            </Button>
            <Button variant="outlined" style={menuButtonDefault}>
                <b>통계</b>
            </Button>
            <Button variant="outlined" style={menuButtonDefault}>
                <b>업적</b>
            </Button>
        </Box>
    )
}
