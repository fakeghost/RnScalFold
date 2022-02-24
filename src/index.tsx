import React from 'react'
import { StyleSheet, Text, View } from 'react-native'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import rootReducer from './reducer'
import thunk from 'redux-thunk'
const store = createStore(rootReducer, applyMiddleware(thunk))

const App = () => {
    return (
        <Provider store={store}>
            <View style={styles.container}>
                <Text>do something......</Text>
            </View>
        </Provider>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
})

export default App
