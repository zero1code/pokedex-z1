package com.z1.pokedex.core.testing

import com.z1.pokedex.core.common.model.google.SubscriptionState
import com.z1.pokedex.core.common.model.google.UserData
import com.z1.pokedex.feature.details.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.Pokemon

val pokemonPage1DataTest = listOf(
    Pokemon(name = "Bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
    Pokemon(name = "Ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"),
    Pokemon(name = "Venusaur", url = "https://pokeapi.co/api/v2/pokemon/3/"),
    Pokemon(name = "Charmander", url = "https://pokeapi.co/api/v2/pokemon/4/"),
    Pokemon(name = "Charmeleon", url = "https://pokeapi.co/api/v2/pokemon/5/"),
    Pokemon(name = "Charizard", url = "https://pokeapi.co/api/v2/pokemon/6/"),
    Pokemon(name = "Squirtle", url = "https://pokeapi.co/api/v2/pokemon/7/"),
    Pokemon(name = "Wartortle", url = "https://pokeapi.co/api/v2/pokemon/8/"),
    Pokemon(name = "Blastoise", url = "https://pokeapi.co/api/v2/pokemon/9/"),
    Pokemon(name = "Caterpie", url = "https://pokeapi.co/api/v2/pokemon/10/"),
    Pokemon(name = "Metapod", url = "https://pokeapi.co/api/v2/pokemon/11/"),
    Pokemon(name = "Butterfree", url = "https://pokeapi.co/api/v2/pokemon/12/"),
    Pokemon(name = "Weedle", url = "https://pokeapi.co/api/v2/pokemon/13/"),
    Pokemon(name = "Kakuna", url = "https://pokeapi.co/api/v2/pokemon/14/"),
    Pokemon(name = "Beedrill", url = "https://pokeapi.co/api/v2/pokemon/15/"),
    Pokemon(name = "Pidgey", url = "https://pokeapi.co/api/v2/pokemon/16/"),
    Pokemon(name = "Pidgeotto", url = "https://pokeapi.co/api/v2/pokemon/17/"),
    Pokemon(name = "Pidgeot", url = "https://pokeapi.co/api/v2/pokemon/18/"),
    Pokemon(name = "Rattata", url = "https://pokeapi.co/api/v2/pokemon/19/"),
    Pokemon(name = "Raticate", url = "https://pokeapi.co/api/v2/pokemon/20/")
)

val pokemonPage2DataTest = listOf(
    Pokemon(name = "Caterpie", url = "https://pokeapi.co/api/v2/pokemon/21/"),
    Pokemon(name = "Metapod", url = "https://pokeapi.co/api/v2/pokemon/22/"),
    Pokemon(name = "Butterfree", url = "https://pokeapi.co/api/v2/pokemon/23/"),
    Pokemon(name = "Weedle", url = "https://pokeapi.co/api/v2/pokemon/24/"),
    Pokemon(name = "Kakuna", url = "https://pokeapi.co/api/v2/pokemon/25/"),
    Pokemon(name = "Beedrill", url = "https://pokeapi.co/api/v2/pokemon/26/"),
    Pokemon(name = "Pidgey", url = "https://pokeapi.co/api/v2/pokemon/27/"),
    Pokemon(name = "Pidgeotto", url = "https://pokeapi.co/api/v2/pokemon/28/"),
    Pokemon(name = "Pidgeot", url = "https://pokeapi.co/api/v2/pokemon/29/"),
    Pokemon(name = "Rattata", url = "https://pokeapi.co/api/v2/pokemon/30/"),
    Pokemon(name = "Raticate", url = "https://pokeapi.co/api/v2/pokemon/31/"),
    Pokemon(name = "Spearow", url = "https://pokeapi.co/api/v2/pokemon/32/"),
    Pokemon(name = "Fearow", url = "https://pokeapi.co/api/v2/pokemon/33/"),
    Pokemon(name = "Ekans", url = "https://pokeapi.co/api/v2/pokemon/34/"),
    Pokemon(name = "Arbok", url = "https://pokeapi.co/api/v2/pokemon/35/"),
    Pokemon(name = "Pikachu", url = "https://pokeapi.co/api/v2/pokemon/36/"),
    Pokemon(name = "Raichu", url = "https://pokeapi.co/api/v2/pokemon/37/"),
    Pokemon(name = "Sandshrew", url = "https://pokeapi.co/api/v2/pokemon/38/"),
    Pokemon(name = "Sandslash", url = "https://pokeapi.co/api/v2/pokemon/39/"),
    Pokemon(name = "Nidoran♀", url = "https://pokeapi.co/api/v2/pokemon/40/")
)

val fakePokemonDetails = PokemonDetails(
    id = 0,
    name = pokemonPage1DataTest[0].name,
    height = 7,
    weight = 7,
    experience = 105
)

val fakeUser = UserData(
    userId = "11111",
    userName = "test",
    profilePictureUrl = ""
)
val fakeSubscription = SubscriptionState(
    subscriptions = listOf(fakeUser.userId)
)