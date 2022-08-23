import { Box } from "@mui/material"

const headerBoxDefault = {
    height: 60,
    border: "1px solid black",
    padding: 0,
    maxWidth: 1000,
    minWidth: 298,
    m: 1
}

const headerButtonDefault = {
    height: 20,
    fontSize: "12px"
}

export default function MenuFooter() {
    return (
        <Box
            display="flex"
            justifyContent="flex-end"
            alignItems="center"
            sx={headerBoxDefault}>
        </Box>
    )
}