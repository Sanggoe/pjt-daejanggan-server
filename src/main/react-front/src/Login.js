import React from 'react';
import "./Login.css"
import { TextField, Checkbox, Button, FormControlLabel, Grid, Link, Typography, Box, Avatar, Container } from '@mui/material';

export default function Login() {
    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}>
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>

                <TextField label="ID"
                    margin='normal'
                    required
                    fullWidth
                    name="email"
                    autoComplete="email"
                    autoFocus />

                <TextField label="Password"
                    margin='normal'
                    type="password"
                    required
                    fullWidth
                    name="password"
                    autoComplete="current-password" />

                <FormControlLabel
                    control={<Checkbox value="remember" color="primary" />}
                    label="Remember me" />

                <Button type="submit"
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}>Sign in</Button>
                <Grid container>
                    <Grid item xs>
                        <Link>Forgot password?</Link>
                    </Grid>
                    <Grid>
                        <Link>Sign Up</Link>
                    </Grid>
                </Grid>
            </Box>
        </Container>
    )
}