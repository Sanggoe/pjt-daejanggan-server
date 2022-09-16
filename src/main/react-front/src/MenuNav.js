import { Button, Box } from "@mui/material"
import { useState } from "react"

const boxDefault = {
    maxWidth: 1000,
    minHeight: 80,
    border: "1px solid black",
}

export default function MenuNav(props) {
    const [color, setColor] = useState('#FFE180');

    const handleClick = (type) => {
        setColor(type);
    }

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>

            {props.menuList.map((menu) => (
                <Button
                    style={{borderRadius: 50, minWidth: 100, color: "black", fontSize: "25px", margin: 10,
                    background: color === menu.type ? color : "#E6E6E6"}}
                    onClick={(event) => {
                        event.preventDefault();
                        props.onChangeMode(menu.label);
                        handleClick(menu.type);
                    }}>
                    <b>{menu.label}</b>
                </Button>
            ))}
        </Box>
    )
}