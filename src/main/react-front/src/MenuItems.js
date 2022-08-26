import { Button, Stack } from "@mui/material"
import { Box } from "@mui/system"
import { useState } from "react"

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

const menuItemDefault = {
    borderRadius: 50,
    minHeight: 65,
    background: "#E6E6E6",
    color: "#7F7F7F",
    fontSize: "25px",
}

export default function MenuItems(props) {
    //const [toggle, setToggle] = useState(false);
    const subContents = <h2>안녕하세용</h2>;

    if

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>
            <Stack
                spacing={2}
                sx={stackDefault}>
                {props.groups.map((group) => (
                        <>
                        <Button
                            style={menuItemDefault}
                            href="./">
                            {group}
                        </Button>
                        {subContents}
                        </>
                ))}
            </Stack>
        </Box>
    )
}
