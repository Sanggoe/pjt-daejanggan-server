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
            {props.menus.map((menu) => (
                <Button
                    id={props.menus}
                    style={menuButtonDefault}
                    onClick={(event) => {
                        event.preventDefault();
                        props.onChangeMode(menu);
                    }}>
                    <b>{menu}</b>
                </Button>
            ))}
        </Box>
    )
}
