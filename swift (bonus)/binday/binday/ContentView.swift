//
//  ContentView.swift
//  binday
//
//  Created by alexsem on 27.01.2024.
//

import SwiftUI

struct ContentView: View {
    var collectionType: String = "recycling"
    var collectionColor: Color {
        switch collectionType {
        case "recycling":
            return .green
        case "refuse":
            return .brown
        default:
            return .blue
        }
    }
    var body: some View {
        HStack {
            Image(systemName: "exclamationmark.3")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundColor(.red)
                .symbolEffect(.variableColor.iterative)
            Spacer()
            VStack {
                Text("It's Bin day!!!")
                    .font(.largeTitle)
                Text("Today is \(collectionType)")
                    .font(.title)
                Image(systemName: "trash.fill")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(.red)
                    .symbolEffect(.pulse.wholeSymbol)
            }
            Spacer()
            Image(systemName: "exclamationmark.3")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundColor(.red)
                .symbolEffect(.variableColor.iterative)
        }
        .padding()
        .background(collectionColor)
        
    }
}

enum CollectionType: String {
    case refuse, recycling
}

#Preview {
    ContentView()
}
