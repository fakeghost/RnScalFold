import React from 'react'
import { SafeAreaView, StyleSheet } from 'react-native'

import Entry from './src/index'

const App = () => {
    return (
        <SafeAreaView style={styles.wrap}>
            <Entry />
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    wrap: {
        flex: 1
    }
})

export default App
