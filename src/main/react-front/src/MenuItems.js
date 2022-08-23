import { Button } from "@mui/material"
import { Box } from "@mui/system"

const boxDefault = {
    height: 400,
    border: "1px solid black",
    padding: 0,
    maxWidth: 1000,
    minWidth: 298,
    m: 1
}

const menuItemDefault = {
    borderRadius: 20,
    minWidth: 200,
    background: "#E6E6E6",
    color: "#7F7F7F",
    fontSize: "18px",
}

export default function MenuItems(props) {
    return (
        <Box
            justifyContent="center"
            alignContent="center"
            sx={boxDefault}>
            {props.groups.map((group) => (
                <p>
                    <Button variant="outlined" style={menuItemDefault} href="./">
                        {group}
                    </Button>
                </p>
            ))}
        </Box>
    )
}